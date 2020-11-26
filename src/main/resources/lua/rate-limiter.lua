--读取keys的第一个key  脚本中转入的
local key1 = KEYS[1]
local key2 = KEYS[2]
--读取脚本中传入的参数  第一个参数  每多少秒访问多少次
local arg1 = tonumber(ARGV[1])
--失效时间
local arg2 = tonumber(ARGV[2])

local count = tonumber(redis.call("get",key1) or "0")


if count+1 > arg1 then
    return false
else
    redis.call("incr",key1)
    redis.call("expire",key1,arg2)
    return true
end










