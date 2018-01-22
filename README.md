# Ika4J
Ika4J is a Java based wrapper library for the Splatoon 2 API.
```
String iksmSession = "…………………………';
ika4j.Schedules schedules = new ika4j.Ika4J(iksmSession).getSchedules();
System.out.println(schedules.getCurrentRankedBattle().getRule().getName());
System.out.println(schedules.getNextRankedBattle().getRule().getName());
```

# Licesne
MIT