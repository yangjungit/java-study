--- 获取令牌
--- 返回码
--- 0 没有令牌桶配置
--- -1 表示取令牌失败，也就是桶里没有令牌
--- 1 表示取令牌成功
--- @param key 令牌的唯一标识
--- @param expire 请求令牌数量
--- @param limit 当前毫秒数
local function acquire(key,expire,limit)
    --redis.log(redis.LOG_DEBUG,"key>"..key)
    --redis.log(redis.LOG_DEBUG,"expire>"..tostring(expire))
    --redis.log(redis.LOG_DEBUG,"limit>"..tostring(limit))
    --执行
    local currCount = redis.call('incr', key)
    local ttl = redis.call('ttl', key)
    --热钱执行结果
    --redis.log(redis.LOG_NOTICE, "currCount>"..currCount);
    --将数据放入redis 中，设置过期时间，过期时间为时间窗口，末过期的数据自增并比对限流值，返回结果

    if currCount == 1 then
        redis.call('EXPIRE', key, tonumber(expire))
    else
        if ttl == -1 then
            redis.call('EXPIRE', key, tonumber(expire))
        end
    end
    --如果当前的数量大于限制数量，返回当前请求数量，否则返回0
    if currCount > tonumber(limit) then
        return currCount
    end
    --获取令牌成功
    return 0
end

--获取KEY
local key = KEYS[1]
--获取ARGV内的参数并打印
local expire = ARGV[1]
local limit = ARGV[2]

return acquire(key,expire,limit)