package com.qexz.controller;

import com.qexz.common.QexzConst;
import com.qexz.dto.AjaxResult;
import com.qexz.model.Account;
import com.qexz.model.PositionType;
import com.qexz.service.PositionTypeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/positionType")
public class PositionTypeController {

    private static Log LOG = LogFactory.getLog(PositionTypeController.class);

    @Autowired
    private PositionTypeService positionTypeService;

    //添加职位
    @RequestMapping(value="/api/addPositionType", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult addPositionType(@RequestBody PositionType position) {
        AjaxResult ajaxResult = new AjaxResult();
        int positionId = positionTypeService.addPositionType(position);
        return new AjaxResult().setData(positionId);
    }

    //更新职位
    @RequestMapping(value="/api/updatePositionType", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult updatePositionType(@RequestBody PositionType position) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = positionTypeService.updatePositionType(position);
        return new AjaxResult().setData(result);
    }

    //删除职位
    @DeleteMapping("/api/deletePositionType/{id}")
    public AjaxResult deletePositionType(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = positionTypeService.deletePositionTypeById(id);
        return new AjaxResult().setData(result);
    }

    /**
     * 分页获取所有职位列表
     */
    @RequestMapping(value = "/api/getPositions", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getPositionTypes(HttpServletRequest request, HttpServletResponse response) {
        AjaxResult ajaxResult = new AjaxResult();
        List<PositionType> data = positionTypeService.getPositionTypes();
        ajaxResult.setData(data);
        return ajaxResult;
    }
}
