package com.mitosis.config.objects

class ProducerConfig {
  
    private var _hosts: List[String] = _
    def hosts = _hosts
    
    private var _batchSize : Int = _
    def batchSize = _batchSize
    
    private var _topic : String = _
    def topic = _topic
}