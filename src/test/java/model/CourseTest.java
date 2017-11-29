package model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Course Tester.
 */
public class CourseTest {

    @Test
    public void testGetCode() throws Exception {
        String ANALISE_DE_ALGORITMO = "TIN0118";
        String BANCO_DE_DADOS_I = "TIN0120";
        Course courseApproved = new Course(ANALISE_DE_ALGORITMO, CourseStatus.APV);
        Assert.assertEquals(courseApproved.getCode(), ANALISE_DE_ALGORITMO);
        Assert.assertNotEquals(courseApproved.getCode(), BANCO_DE_DADOS_I);
    }

    @Test
    public void testGetCourseType() throws Exception {
        String ANALISE_DE_ALGORITMO = "TIN0118";
        String COMPILADORES = "TIN0146";
        Course courseMandatory = new Course(ANALISE_DE_ALGORITMO, CourseStatus.APV);
        Course courseOptional = new Course(COMPILADORES, CourseStatus.APV);
        Assert.assertEquals(courseMandatory.getCourseType(), CourseType.MANDATORY);
        Assert.assertNotEquals(courseMandatory.getCourseType(), CourseType.OPTIONAL);
        Assert.assertEquals(courseOptional.getCourseType(), CourseType.OPTIONAL);
        Assert.assertNotEquals(courseOptional.getCourseType(), CourseType.MANDATORY);
    }

    @Test
    public void testGetFlunksQuantity() throws Exception {
        //TODO: test
    }

    @Test
    public void testAddStatus() throws Exception {
        //TODO: test
    }

    @Test
    public void testLastStatus() throws Exception {
        String ANALISE_DE_ALGORITMO = "TIN0118";
        Course courseApproved = new Course(ANALISE_DE_ALGORITMO, CourseStatus.REP);
        courseApproved.addStatus(CourseStatus.REF);
        courseApproved.addStatus(CourseStatus.APV);
        Assert.assertEquals(courseApproved.lastStatus(), CourseStatus.APV);
        Assert.assertNotEquals(courseApproved.lastStatus(), CourseStatus.REF);
        Assert.assertNotEquals(courseApproved.lastStatus(), CourseStatus.REP);
    }

    @Test
    public void testToString() throws Exception {
        //TODO: test
    }

    @Test
    public void testEvaluateCourseType() throws Exception {
        //TODO: test
    }

} 
