//package com.qexz.service;
//
//import com.qexz.common.QexzConst;
//import com.qexz.model.position;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ActiveProfiles("dev")
//public class positionServiceTest {
//
//    private static Log LOG = LogFactory.getLog(positionServiceTest.class);
//
//    @Autowired
//    private positionService positionService;
//
//    @Test
//    public void addposition() throws Exception {
////        position position = new position();
////        position.setName("计算机组成原理");
////        int result = positionService.addposition(position);
////        LOG.info("result = " + result);
//
//        position position1 = new position();
//        position1.setName("C语言程序设计");
//        position1.setImgUrl("problemset_c.jpg");
//        int result = positionService.addposition(position1);
//        LOG.info("result = " + result);
//
//        position position2 = new position();
//        position2.setName("Java语言程序设计");
//        position2.setImgUrl("problemset_java.jpg");
//        result = positionService.addposition(position2);
//        LOG.info("result = " + result);
//
//        position position3 = new position();
//        position3.setName("C++语言程序设计");
//        position3.setImgUrl("problemset_c++.jpg");
//        result = positionService.addposition(position3);
//        LOG.info("result = " + result);
//
//        position position4 = new position();
//        position4.setName("Python语言程序设计");
//        position4.setImgUrl("problemset_python.jpg");
//        result = positionService.addposition(position4);
//        LOG.info("result = " + result);
//
//        position position5 = new position();
//        position5.setName("数据结构与算法");
//        position5.setImgUrl("problemset_datastructures.jpg");
//        result = positionService.addposition(position5);
//        LOG.info("result = " + result);
//
//        position position6 = new position();
//        position6.setName("数据结构与算法");
//        position6.setImgUrl("problemset_datastructures.jpg");
//        result = positionService.addposition(position6);
//        LOG.info("result = " + result);
//
//        position position7 = new position();
//        position7.setName("数据库概论");
//        position7.setImgUrl("problemset_database.jpg");
//        result = positionService.addposition(position7);
//        LOG.info("result = " + result);
//    }
//
//    @Test
//    public void updateposition() throws Exception {
//        position position = positionService.getpositionById(7);
//        position.setName("软件测试");
//        position.setImgUrl("problemset_softwareTest.jpg");
//        boolean result = positionService.updateposition(position);
//        LOG.info("result = " + result);
//    }
//
//    @Test
//    public void getpositionById() throws Exception {
//        position position = positionService.getpositionById(7);
//        LOG.info("position = " + position);
//    }
//
//    @Test
//    public void getpositions() throws Exception {
//        Map<String, Object> data = positionService.getpositions(100, 1);
//        List<position> positions = (List<position>) data.getOrDefault("positions", new ArrayList<>());
//        for (position position : positions) {
//            LOG.info("position = " + position);
//        }
//    }
//
//}