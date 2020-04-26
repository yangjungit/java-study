--
-- Created by IntelliJ IDEA.
-- User: yj12922
-- Date: 2020/4/26
-- Time: 14:13
-- To change this template use File | Settings | File Templates.
--
--读取keys的第一个key  脚本中转入的
local key1 = KEYS[1]
--读取脚本中传入的参数  第一个参数  每多少秒访问多少次
local token = tonumber(ARGV[1])
--token 生成间隔时间  也是可以的失效时间
local intervals = tonumber(ARGV[2])
--key失效时间单位
local exType = ARGV[3]

local count = tonumber(redis.call("get", key1) or "0")

if count + 1 > token then
    return false
else
    redis.call("incr", key1)
    if exType == "MILLISECOND" then
        redis.call("pexpire", key1, intervals)
    else
        redis.call("expire", key1, intervals)
    end
    return true
end



