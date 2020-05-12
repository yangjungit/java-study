package com.giovanny.study.streamstudy;

import com.giovanny.study.entity.StudentInfo;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @packageName: com.giovanny.study.streamstudy
 * @className: StreamDemo2
 * @description: StreamDemo2
 * @author: YangJun
 * @date: 2020/5/12 9:32
 * @version: v1.0
 **/
public class StreamDemo2 {

    public static void main(String[] args) {
        // 初始化
        List<StudentInfo> students = new ArrayList<StudentInfo>() {
            {
                add(new StudentInfo(20160001, "孔明", 20, 1, "土木工程", "武汉大学"));
                add(new StudentInfo(20160002, "伯约", 21, 2, "信息安全", "武汉大学"));
                add(new StudentInfo(20160003, "玄德", 22, 3, "经济管理", "武汉大学"));
                add(new StudentInfo(20160004, "云长", 21, 2, "信息安全", "武汉大学"));
                add(new StudentInfo(20160004, "云长", 21, 2, "信息安全", "武汉大学"));
                add(new StudentInfo(20161001, "翼德", 19, 2, "机械与自动化", "华中科技大学"));
                add(new StudentInfo(20161002, "元直", 23, 4, "土木工程", "华中科技大学"));
                add(new StudentInfo(20161003, "奉孝", 23, 4, "计算机科学", "华中科技大学"));
                add(new StudentInfo(20162001, "仲谋", 22, 3, "土木工程", "浙江大学"));
                add(new StudentInfo(20162002, "鲁肃", 23, 4, "计算机科学", "浙江大学"));
                add(new StudentInfo(20163001, "丁奉", 19, 5, "土木工程", "南京大学"));
            }
        };

        //现在我们希望从集合students中筛选出所有武汉大学的学生，那么我们可以通过filter来实现，并将筛选操作作为参数传递给filter：
        List<StudentInfo> list1 = students
                .stream()
                .filter(stu -> "武汉大学".equals(stu.getSchool()))
                .collect(Collectors.toList());
        list1.forEach(System.out::println);
        //distinct操作类似于我们在写SQL语句时，添加的DISTINCT关键字，用于去重处理，distinct基于Object.equals(Object)实现，
        //回到最开始的例子，假设我们希望筛选出所有不重复的偶数，那么可以添加distinct操作：
        List<StudentInfo> list2 = students
                .stream()
                .filter(stu -> "武汉大学".equals(stu.getSchool()))
                .distinct()
                .collect(Collectors.toList());
        System.out.println("-----------------list2----------------");
        list2.forEach(System.out::println);

        //limit操作也类似于SQL语句中的LIMIT关键字，不过相对功能较弱，limit返回包含前n个元素的流，
        //当集合大小小于n时，则返回实际长度，比如下面的例子返回前两个学校为武汉大学的学生：
        List<StudentInfo> list3 = students
                .stream()
                .filter(stu -> "武汉大学".equals(stu.getSchool()))
                .limit(2)
                .collect(Collectors.toList());
        System.out.println("---------------list3------------------");
        list3.forEach(System.out::println);

        // 说到limit，不得不提及一下另外一个流操作：sorted。
        // 该操作用于对流中元素进行排序，sorted要求待比较的元素必须实现Comparable接口，
        // 如果没有实现也不要紧，我们可以将比较器作为参数传递给sorted(Comparator<? super T> comparator)，
        // 比如我们希望筛选出专业为土木工程的学生，并按年龄从小到大排序，筛选出年龄最小的两个学生，那么可以实现为：
        List<StudentInfo> list4 = students
                .stream()
                .filter(stu -> "土木工程".equals(stu.getMajor()))
                .sorted(Comparator.comparingInt(StudentInfo::getAge))
                .limit(2)
                .collect(Collectors.toList());
        System.out.println("--------------list4-------------------");
        list4.forEach(System.out::println);

        //skip操作与limit操作相反，如同其字面意思一样，是跳过前n个元素，比如我们希望找出排序在2之后的土木工程专业的学生，那么可以实现为：
        List<StudentInfo> list5 = students
                .stream()
                .filter(stu -> "土木工程".equals(stu.getMajor()))
                .sorted(Comparator.comparingInt(StudentInfo::getAge))
                .skip(2)
                .collect(Collectors.toList());
        System.out.println("--------------list5-------------------");
        list5.forEach(System.out::println);

        // 举例说明，假设我们希望筛选出所有专业为计算机科学的学生姓名，
        // 那么我们可以在filter筛选的基础之上，通过map将学生实体映射成为学生姓名字符串，具体实现如下：
        List<String> list6 = students
                .stream()
                .filter(stu -> "计算机科学".equals(stu.getMajor()))
                .map(StudentInfo::getName)
                .collect(Collectors.toList());
        System.out.println("--------------list6-------------------");
        list6.forEach(System.out::println);

        //除了上面这类基础的map，java8还提供了
        // mapToDouble(ToDoubleFunction<? super T> mapper)，
        // mapToInt(ToIntFunction<? super T> mapper)，
        // mapToLong(ToLongFunction<? super T> mapper)，
        // 这些映射分别返回对应类型的流，java8为这些流设定了一些特殊的操作，
        // 比如我们希望计算专业为计算机科学学生的年龄之和，那么我们可以实现如下：
        int ageSum = students
                .stream()
                .filter(stu -> "计算机科学".equals(stu.getMajor()))
                .mapToInt(StudentInfo::getAge)
                .sum();
        System.out.println("--------------ageSum-------------------");
        System.out.println(ageSum);

        // flatMap与map的区别在于 flatMap是将一个流中的每个值都转成一个个流，然后再将这些流扁平化成为一个流 。
        // 举例说明，假设我们有一个字符串数组String[] strs = {"java8", "is", "easy", "to", "use"};
        // 我们希望输出构成这一数组的所有非重复字符，那么我们可能首先会想到如下实现：
        String[] strs = {"java8", "is", "easy", "to", "use"};
        List<String[]> distinctStrs1 = Arrays.stream(strs)
                // 映射成为Stream<String[]>
                .map(str -> str.split(""))
                .distinct()
                .collect(Collectors.toList());
        System.out.println("--------------distinctStrs-------------------");
        distinctStrs1.forEach(strings -> System.out.println(Arrays.toString(strings)));


        List<String> distinctStrs2 = Arrays.stream(strs)
                // 映射成为Stream<String[]>
                .map(str -> str.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("--------------distinctStrs-------------------");
        distinctStrs2.forEach(System.out::println);

        // allMatch用于检测是否全部都满足指定的参数行为，如果全部满足则返回true，
        // 例如我们希望检测是否所有的学生都已满18周岁，那么可以实现为：
        boolean allMatch = students.stream()
                .allMatch(studentInfo -> studentInfo.getAge() > 18);
        System.out.println("allMatch=" + allMatch);

        // noneMatch用于检测是否不存在满足指定行为的元素，如果不存在则返回true，
        // 例如我们希望检测是否不存在专业为计算机科学的学生，可以实现如下：
        boolean noneMatch = students.stream()
                .noneMatch(studentInfo -> "计算机科学".equals(studentInfo.getMajor()));
        System.out.println("noneMatch=" + noneMatch);

        // anyMatch则是检测是否存在一个或多个满足指定的参数行为，如果满足则返回true，
        // 例如我们希望检测是否有来自武汉大学的学生，那么可以实现为：
        boolean anyMatch = students.stream()
                .anyMatch(studentInfo -> "武汉大学".equals(studentInfo.getSchool()));
        System.out.println("anyMatch=" + anyMatch);

        //findFirst用于返回满足条件的第一个元素，比如我们希望选出专业为土木工程的排在第一个学生，那么可以实现如下：
        //关于Optional的介绍：https://blog.csdn.net/fly910905/article/details/87533628
        StudentInfo first = students.stream()
                .filter(studentInfo -> "土木工程".equals(studentInfo.getMajor()))
                .findFirst()
                .orElse(null);
        System.out.println("first=" + first);

        // findAny相对于findFirst的区别在于，findAny不一定返回第一个，而是返回任意一个，
        // 比如我们希望返回任意一个专业为土木工程的学生，可以实现如下：
        StudentInfo findAny = students.stream()
                .filter(studentInfo -> "土木工程".equals(studentInfo.getMajor()))
                .findAny()
                .orElse(null);
        System.out.println("findAny=" + findAny);

        // 实际上对于顺序流式处理而言，findFirst和findAny返回的结果是一样的，
        // 至于为什么会这样设计，是因为在下一篇我们介绍的并行流式处理，
        // 当我们启用并行流式处理的时候，查找第一个元素往往会有很多限制，
        // 如果不是特别需求，在并行流式处理中使用findAny的性能要比findFirst好

        // 前面的例子中我们大部分都是通过collect(Collectors.toList())对数据封装返回，
        // 如我的目标不是返回一个新的集合，而是希望对经过参数化操作后的集合进行进一步的运算，那么我们可用对集合实施归约操作。
        // java8的流式处理提供了reduce方法来达到这一目的。
        // 前面我们通过mapToInt将Stream<Student>映射成为IntStream，并通过IntStream的sum方法求得所有学生的年龄之和，
        // 实际上我们通过归约操作，也可以达到这一目的，实现如下：
        Integer reduceSum1 = students.stream()
                .filter(studentInfo -> "计算机科学".equals(studentInfo.getMajor()))
                .map(StudentInfo::getAge)
                .reduce(0, Integer::sum);
        Integer reduceSum2 = students.stream()
                .filter(studentInfo -> "计算机科学".equals(studentInfo.getMajor()))
                .map(StudentInfo::getAge)
                .reduce(Integer::sum)
                .orElse(-1);
        System.out.println("reduceSum1=" + reduceSum1 + "," + "reduceSum2=" + reduceSum2);

        // 前面利用collect(Collectors.toList())是一个简单的收集操作，是对处理结果的封装，
        // 对应的还有toSet、toMap，以满足我们对于结果组织的需求。
        // 这些方法均来自于java.util.stream.Collectors，我们可以称之为收集器。

        // 求学生的总人数
        long collect2 = students.stream()
                .filter(studentInfo -> studentInfo.getAge() > 20)
                .count();
        System.out.println("collect2=" + collect2);

        // 求最大年龄 有多个只返回第一个
        StudentInfo studentInfo = students.stream()
                .max(Comparator.comparingInt(StudentInfo::getAge))
                .orElse(null);
        System.out.println(studentInfo);

        // 求平均年龄
        Double ageAvg = students.stream().collect(Collectors.averagingDouble(StudentInfo::getAge));
        System.out.println("ageAvg=" + ageAvg);

        //一次性得到元素个数、总和、均值、最大值、最小值
        IntSummaryStatistics statistics = students.stream().collect(Collectors.summarizingInt(StudentInfo::getAge));
        System.out.println(statistics);

        //字符串拼接
        String names = students.stream()
                .map(StudentInfo::getName)
                .collect(Collectors.joining(","));
        System.out.println(names);

        // 在数据库操作中，我们可以通过GROUP BY关键字对查询到的数据进行分组，
        // java8的流式处理也为我们提供了这样的功能Collectors.groupingBy来操作集合。比如我们可以按学校对上面的学生进行分组：
        Map<String, List<StudentInfo>> collect = students.stream()
                .collect(Collectors.groupingBy(StudentInfo::getSchool));
        System.out.println(collect);

        Map<String, Map<String, List<StudentInfo>>> collect1 = students.stream().collect(
                Collectors.groupingBy(StudentInfo::getSchool,
                        Collectors.groupingBy(StudentInfo::getMajor)));
        System.out.println(collect1);

        Map<String, Long> collect3 = students.stream().collect(
                Collectors.groupingBy(StudentInfo::getSchool,
                        Collectors.counting()));
        System.out.println(collect3);

        // 分区可以看做是分组的一种特殊情况，在分区中key只有两种情况：true或false，目的是将待分区集合按照条件一分为二，
        // java8的流式处理利用collectors.partitioningBy()方法实现分区，该方法接收一个谓词，
        // 例如我们希望将学生分为武大学生和非武大学生，那么可以实现如下：
        Map<Boolean, List<StudentInfo>> collect4 = students
                .stream()
                .collect(Collectors.partitioningBy(studentInfo1 -> "武汉大学".equals(studentInfo1.getSchool())));
        System.out.println(collect4);


        /*
         * 流式处理中的很多都适合采用 分而治之 的思想，从而在处理集合较大时，极大的提高代码的性能，java8的设计者也看到了这一点，所以提供了 并行流式处理。
         * 上面的例子中我们都是调用stream()方法来启动流式处理，java8还提供了parallelStream()来启动并行流式处理，
         * parallelStream()本质上基于java7的Fork-Join框架实现，其默认的线程数为宿主机的内核数。
         * 启动并行流式处理虽然简单，只需要将stream()替换成parallelStream()即可，
         * 但既然是并行，就会涉及到多线程安全问题，所以在启用之前要先确认并行是否值得（并行的效率不一定高于顺序执行），另外就是要保证线程安全。
         * 此两项无法保证，那么并行毫无意义，毕竟结果比速度更加重要，以后有时间再来详细分析一下并行流式数据处理的具体实现和最佳实践。
         * */
    }


}
