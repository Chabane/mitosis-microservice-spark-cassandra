package com.mitosis.config

import com.google.common.base.Strings
import com.mitosis.config.objects.Config
import com.typesafe.config.ConfigBeanFactory
import com.typesafe.config.ConfigFactory

import java.io.File

object ConfigurationFactory {

  /**
    * Loads configuration object by file
    *
    * @return Returns configuration objects
    */
  def load(): Config = {
    val file = getClass.getClassLoader.getResource("app.conf").getFile()
    val config: com.typesafe.config.Config = ConfigFactory.parseFile(new File(file))
    ConfigBeanFactory.create(config, classOf[Config])
  }

}

