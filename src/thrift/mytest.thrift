namespace java thriftGenerated

typedef i8 short
typedef i16 int
typedef i32 long
typedef string String
typedef bool boolean



struct Agent{
    1: optional String ip,
    2: optional double cpu_free,
    3: optional double memory_free,
    4: optional double disk_free,
    5: optional double cpu_total,
    6: optional double memory_total,
    7: optional double disk_total,
    8: optional long time_stamp
}

exception DataException{
    1: optional String message,
    2: optional String date
}

service AgentService{
    Agent sendAgentByIP(1: required String ip, 2: required Agent agent) throws (1: DataException dataException),

}

