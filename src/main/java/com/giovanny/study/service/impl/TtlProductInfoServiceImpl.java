package com.giovanny.study.service.impl;

import com.giovanny.study.constant.ExcelFormat;
import com.giovanny.study.entity.ExcelHeaderInfo;
import com.giovanny.study.entity.po.TtlProductInfoPo;
import com.giovanny.study.mapper.TtlProductInfoMapper;
import com.giovanny.study.poi.ExcelUtil;
import com.giovanny.study.service.TtlProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.logging.SimpleFormatter;


/**
 * @author yangjun
 */
@Service
@Slf4j
public class TtlProductInfoServiceImpl implements TtlProductInfoService {

    // 每个线程导出记录最大行数
    private static final int THREAD_MAX_ROW = 20000;

    @Autowired
    private TtlProductInfoMapper ttlProductInfoMapper;

    @Override
    public void insertN(Integer num) {
        StopWatch watch = new StopWatch("insertN");
        watch.start();
        Integer loop = new Double(Math.ceil((num / 1000.0))).intValue();
        for (int j = 0; j < loop; j++) {
            List<TtlProductInfoPo> list = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                TtlProductInfoPo productInfoPo = new TtlProductInfoPo();
                productInfoPo.setProductName("productName-" + j + "-" + i);
                productInfoPo.setCategoryId((long) (j + i));
                productInfoPo.setCategoryName("categoryName" + j + "-" + i);
                productInfoPo.setBranchId((long) (j + i));
                productInfoPo.setBranchName("branchName" + j + "-" + i);
                productInfoPo.setShopId((long) (j + i));
                productInfoPo.setShopName("shopName" + j + "-" + i);
                productInfoPo.setPrice((double) (j + i));
                productInfoPo.setStock(j + i);
                productInfoPo.setSalesNum(j + i);
                String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                productInfoPo.setCreateTime(format);
                productInfoPo.setUpdateTime(format);
                productInfoPo.setIsDel((byte) 0);
                list.add(productInfoPo);
            }
            ttlProductInfoMapper.insertList(list);
            list.clear();
        }
        watch.stop();
        log.info(watch.prettyPrint());
    }

    @Override
    public List<TtlProductInfoPo> listProduct(Map<String, Object> map) {
        return this.ttlProductInfoMapper.listProduct(map);
    }

    @Override
    public void export(HttpServletResponse response, String fileName) {
        // 待导出数据
        List<TtlProductInfoPo> productInfoPos = this.multiThreadListProduct();
        ExcelUtil excelUtil = new ExcelUtil(productInfoPos, getHeaderInfo(), getFormatInfo());
        excelUtil.sendHttpResponse(response, fileName, excelUtil.getWorkbook());
    }

    // 获取表头信息
    private List<ExcelHeaderInfo> getHeaderInfo() {
        return Arrays.asList(
                new ExcelHeaderInfo(1, 1, 0, 0, "id"),
                new ExcelHeaderInfo(1, 1, 1, 1, "商品名称"),

                new ExcelHeaderInfo(0, 0, 2, 3, "分类"),
                new ExcelHeaderInfo(1, 1, 2, 2, "类型ID"),
                new ExcelHeaderInfo(1, 1, 3, 3, "分类名称"),

                new ExcelHeaderInfo(0, 0, 4, 5, "品牌"),
                new ExcelHeaderInfo(1, 1, 4, 4, "品牌ID"),
                new ExcelHeaderInfo(1, 1, 5, 5, "品牌名称"),

                new ExcelHeaderInfo(0, 0, 6, 7, "商店"),
                new ExcelHeaderInfo(1, 1, 6, 6, "商店ID"),
                new ExcelHeaderInfo(1, 1, 7, 7, "商店名称"),

                new ExcelHeaderInfo(1, 1, 8, 8, "价格"),
                new ExcelHeaderInfo(1, 1, 9, 9, "库存"),
                new ExcelHeaderInfo(1, 1, 10, 10, "销量"),
                new ExcelHeaderInfo(1, 1, 11, 11, "插入时间"),
                new ExcelHeaderInfo(1, 1, 12, 12, "更新时间"),
                new ExcelHeaderInfo(1, 1, 13, 13, "记录是否已经删除")
        );
    }

    // 获取格式化信息
    private Map<String, ExcelFormat> getFormatInfo() {
        Map<String, ExcelFormat> format = new HashMap<>();
        format.put("id", ExcelFormat.FORMAT_INTEGER);
        format.put("categoryId", ExcelFormat.FORMAT_INTEGER);
        format.put("branchId", ExcelFormat.FORMAT_INTEGER);
        format.put("shopId", ExcelFormat.FORMAT_INTEGER);
        format.put("price", ExcelFormat.FORMAT_DOUBLE);
        format.put("stock", ExcelFormat.FORMAT_INTEGER);
        format.put("salesNum", ExcelFormat.FORMAT_INTEGER);
        format.put("isDel", ExcelFormat.FORMAT_INTEGER);
        return format;
    }

    // 多线程查询报表
    private List<TtlProductInfoPo> multiThreadListProduct() {
        List<FutureTask<List<TtlProductInfoPo>>> tasks = new ArrayList<>();
        List<TtlProductInfoPo> productInfoPos = new ArrayList<>();

        int totalNum = 500000;
        int loopNum = new Double(Math.ceil((double) totalNum / THREAD_MAX_ROW)).intValue();
        log.info("多线程查询，总数：{},开启线程数：{}", totalNum, loopNum);
        long start = System.currentTimeMillis();

        executeTask(tasks, loopNum, totalNum);

        for (FutureTask<List<TtlProductInfoPo>> task : tasks) {
            try {
                productInfoPos.addAll(task.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        log.info("查询结束，耗时:{}", System.currentTimeMillis() - start);
        return productInfoPos;
    }

    // 执行查询任务
    private void executeTask(List<FutureTask<List<TtlProductInfoPo>>> tasks, int loopNum, int total) {
        for (int i = 0; i < loopNum; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("offset", i * THREAD_MAX_ROW);
            if (i == loopNum - 1) {
                map.put("limit", total - THREAD_MAX_ROW * i);
            } else {
                map.put("limit", THREAD_MAX_ROW);
            }
            FutureTask<List<TtlProductInfoPo>> task = new FutureTask<>(new listThread(map));
            log.info("开始查询第{}条开始的{}条记录", i * THREAD_MAX_ROW, THREAD_MAX_ROW);
            new Thread(task).start();
            // 将任务添加到tasks列表中
            tasks.add(task);
        }
    }

    private class listThread implements Callable<List<TtlProductInfoPo>> {

        private Map<String, Object> map;

        private listThread(Map<String, Object> map) {
            this.map = map;
        }

        @Override
        public List<TtlProductInfoPo> call() {
            return listProduct(map);
        }
    }

}
