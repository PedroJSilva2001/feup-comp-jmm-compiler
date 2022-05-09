package pt.up.fe.comp;

import org.junit.Test;
import pt.up.fe.comp.jmm.jasmin.JasminResult;
import pt.up.fe.specs.util.SpecsIo;

import static org.junit.Assert.assertEquals;

public class BackEndTest {

    @Test
    public void testFac() {
        JasminResult jasminResult = TestUtils.backend(SpecsIo.getResource("fixtures/public/Fac.jmm"));
        jasminResult.compile();
        assertEquals("3628800\r\n", jasminResult.run());
        TestUtils.noErrors(jasminResult);
    }

//    @Test
//    public void testMonteCarloPi() {
//        // this requires input
//        JasminResult jasminResult = TestUtils.backend(SpecsIo.getResource("fixtures/public/Fac.jmm"));
//        jasminResult.compile();
//        assertEquals("314\r\n", jasminResult.run());
//        TestUtils.noErrors(jasminResult);
//    }

    @Test
    public void testSelfMade() {
        JasminResult jasminResult = TestUtils.backend(SpecsIo.getResource("fixtures/public/SelfMade1.jmm"));
        jasminResult.compile();
        assertEquals("5\r\n", jasminResult.run());
        TestUtils.noErrors(jasminResult);
    }

    @Test
    public void testBinomialCoefficient() {
        JasminResult jasminResult = TestUtils.backend(SpecsIo.getResource("fixtures/public/BinomialCoefficient.jmm"));
        jasminResult.compile();
        assertEquals("28\r\n", jasminResult.run());
        TestUtils.noErrors(jasminResult);
    }

}
