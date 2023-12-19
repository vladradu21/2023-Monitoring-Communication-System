# 2023-Monitoring-Communication-System
<h4>The monitoring microservice obtains data from the data simulator microservice, which supplies device-related information. It checks hourly consumption against specific device thresholds. When there's an overflow, it uses websockets to notify the frontend.</h4>

![image](https://github.com/vladradu21/2023-Monitoring-Communication-System/assets/117584846/36807978-c097-4c47-a42e-488f4ae43464)


<h4>Both the hourly and maximum consumption details are stored in the database.</h4>

![image](https://github.com/vladradu21/2023-Monitoring-Communication-System/assets/117584846/a075fe25-998f-406b-99ac-a17580459044)
