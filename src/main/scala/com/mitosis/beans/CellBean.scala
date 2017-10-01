package com.mitosis.beans

import CellType._

class CellBean {

    private var _name: String = _
    def name = _name
    
    private var _cellType: CellType = _
    def getType = _cellType
}