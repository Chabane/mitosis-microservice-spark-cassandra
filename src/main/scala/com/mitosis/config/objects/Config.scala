package com.mitosis.config.objects

class Config {
    private var _producer: ProducerConfig = _
    def producer = _producer
    
    private var _streaming: StreamingConfig = _
    def streaming = _streaming
}