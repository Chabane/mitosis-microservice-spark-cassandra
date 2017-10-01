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
    if (Strings.isNullOrEmpty(System.getProperty("config"))) {
      throw new RuntimeException(
        "Configuration file path is empty. " +
          "Please specify the file path with using -Dconfig=[PATH]")
    }
    val config: com.typesafe.config.Config =
      ConfigFactory.parseFile(new File(System.getProperty("config")))
    ConfigBeanFactory.create(config, classOf[Config])
  }

}

