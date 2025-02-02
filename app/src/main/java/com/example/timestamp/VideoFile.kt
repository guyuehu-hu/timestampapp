package com.example.timestamp

// 数据类用于存储文件名和时间信息
data class VideoFile(
      val fileName: String,     // 文件名如"124-C.mp4"
      var startTime: String = "00:00:00",  // 开始时间默认值
      var endTime: String = "00:00:00"     // 结束时间默认值
  )
