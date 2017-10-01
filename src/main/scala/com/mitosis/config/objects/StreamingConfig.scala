package com.mitosis.config.objects

class StreamingConfig {
  
    private var _db: StreamingDbConfig = _
    def db = _db
    
    private var _window: Int = _
    def window = _window
}