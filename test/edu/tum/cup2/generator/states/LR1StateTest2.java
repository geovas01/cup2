package edu.tum.cup2.generator.states;

import java.util.HashSet;

import org.junit.Test;

import edu.tum.cup2.generator.items.LR1Item;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Production;
import edu.tum.cup2.grammar.Terminal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/*
 * Bedingungen:
 *
 * a)    U(i) disjoint U'(j) && U'(i) disjoint U(j)
 * b)    U(i) not disjoint U(j)
 * c)    U'(i) not disjoint U'(j)
 *
 */
public class LR1StateTest2 {

    public enum T implements Terminal {
        $, a, b, c, d, e, f, x1, x2, x3, x4, x5, x6;
    }

    public enum N implements NonTerminal {
        S, T, A, B, C, D, E, F, X, Y, Z;
    }

    HashSet<Terminal> em = new HashSet<Terminal>();
    HashSet<Terminal> la = new HashSet<Terminal>();
    HashSet<Terminal> lb = new HashSet<Terminal>();
    HashSet<Terminal> lc = new HashSet<Terminal>();
    HashSet<Terminal> ld = new HashSet<Terminal>();
    HashSet<Terminal> la1 = new HashSet<Terminal>();
    HashSet<Terminal> la2 = new HashSet<Terminal>();
    HashSet<Terminal> lb1 = new HashSet<Terminal>();
    HashSet<Terminal> lb2 = new HashSet<Terminal>();
    HashSet<Terminal> lc1 = new HashSet<Terminal>();
    HashSet<Terminal> lc2 = new HashSet<Terminal>();
    HashSet<Terminal> ld1 = new HashSet<Terminal>();
    HashSet<Terminal> ld2 = new HashSet<Terminal>();

    {
        la.add(T.a);
        lb.add(T.b);
        lc.add(T.c);
        ld.add(T.d);
        la1.add(T.a);
        lb1.add(T.b);
        lc1.add(T.c);
        ld1.add(T.d);
        la1.add(T.x1);
        lb1.add(T.x1);
        lc1.add(T.x1);
        ld1.add(T.x1);
        la2.add(T.a);
        lb2.add(T.b);
        lc2.add(T.c);
        ld2.add(T.d);
        la2.add(T.x2);
        lb2.add(T.x2);
        lc2.add(T.x2);
        ld2.add(T.x2);

    }

    Production pA = new Production(1, N.S, N.A);
    Production pB = new Production(2, N.S, N.B);
    Production pC = new Production(3, N.S, N.C);

    LR1Item S_oA_em = new LR1Item(pA, 0, em);
    LR1Item S_oA_a = new LR1Item(pA, 0, la);
    LR1Item S_oA_a_x1 = new LR1Item(pA, 0, la1);
    LR1Item S_oA_a_x2 = new LR1Item(pA, 0, la2);
    LR1Item S_oA_b = new LR1Item(pA, 0, lb);
    LR1Item S_oA_b_x1 = new LR1Item(pA, 0, lb1);
    LR1Item S_oA_b_x2 = new LR1Item(pA, 0, lb2);
    LR1Item S_oA_c = new LR1Item(pA, 0, lc);
    LR1Item S_oA_c_x1 = new LR1Item(pA, 0, lc1);
    LR1Item S_oA_c_x2 = new LR1Item(pA, 0, lc2);
    LR1Item S_oB_em = new LR1Item(pB, 0, em);
    LR1Item S_oB_a = new LR1Item(pB, 0, la);
    LR1Item S_oB_a_x1 = new LR1Item(pB, 0, la1);
    LR1Item S_oB_a_x2 = new LR1Item(pB, 0, la2);
    LR1Item S_oB_b = new LR1Item(pB, 0, lb);
    LR1Item S_oB_b_x1 = new LR1Item(pB, 0, lb1);
    LR1Item S_oB_b_x2 = new LR1Item(pB, 0, lb2);
    LR1Item S_oB_c = new LR1Item(pB, 0, lc);
    LR1Item S_oB_c_x1 = new LR1Item(pB, 0, lc1);
    LR1Item S_oB_c_x2 = new LR1Item(pB, 0, lc2);

    LR1Item S_oC_c = new LR1Item(pC, 0, lc);

    LR1Item S_Ao_em = new LR1Item(pA, 1, em);
    LR1Item S_Ao_a = new LR1Item(pA, 1, la);
    LR1Item S_Ao_a_x1 = new LR1Item(pA, 1, la1);
    LR1Item S_Ao_a_x2 = new LR1Item(pA, 1, la2);
    LR1Item S_Ao_b = new LR1Item(pA, 1, lb);
    LR1Item S_Ao_b_x1 = new LR1Item(pA, 1, lb1);
    LR1Item S_Ao_b_x2 = new LR1Item(pA, 1, lb2);
    LR1Item S_Ao_c = new LR1Item(pA, 1, lc);
    LR1Item S_Ao_c_x1 = new LR1Item(pA, 1, lc1);
    LR1Item S_Ao_c_x2 = new LR1Item(pA, 1, lc2);
    LR1Item S_Bo_em = new LR1Item(pB, 1,em);
    LR1Item S_Bo_a = new LR1Item(pB, 1, la);
    LR1Item S_Bo_a_x1 = new LR1Item(pB, 1, la1);
    LR1Item S_Bo_a_x2 = new LR1Item(pB, 1, la2);
    LR1Item S_Bo_b = new LR1Item(pB, 1, lb);
    LR1Item S_Bo_b_x1 = new LR1Item(pB, 1, lb1);
    LR1Item S_Bo_b_x2 = new LR1Item(pB, 1, lb2);
    LR1Item S_Bo_c = new LR1Item(pB, 1, lc);
    LR1Item S_Bo_c_x1 = new LR1Item(pB, 1, lc1);
    LR1Item S_Bo_c_x2 = new LR1Item(pB, 1, lc2);

    LR1Item S_Co_c = new LR1Item(pC, 1, lc);
    LR1Item S_Co_d = new LR1Item(pC, 1, ld);


    LR1State AaBb = new LR1State(toSet(S_oA_a, S_oB_b));
    LR1State AbBa = new LR1State(toSet(S_oA_b, S_oB_a));
    LR1State AcBa = new LR1State(toSet(S_oA_c, S_oB_a));
    LR1State AcBb = new LR1State(toSet(S_oA_c, S_oB_b));
    LR1State AaBc = new LR1State(toSet(S_oA_a, S_oB_c));
    LR1State AbBc = new LR1State(toSet(S_oA_b, S_oB_c));

    LR1State AaCc = new LR1State(toSet(S_oA_b, S_oC_c));

    LR1State nAaBb = new LR1State(toSet(S_Ao_a, S_Bo_b));
    LR1State nAbBa = new LR1State(toSet(S_Ao_b, S_Bo_a));

    LR1State nAa1Bb1 = new LR1State(toSet(S_Ao_a_x1, S_Bo_b_x1));
    LR1State nAb2Ba2 = new LR1State(toSet(S_Ao_b_x2, S_Bo_a_x2));

    LR1State nAaBbCc = new LR1State(toSet(S_Ao_a, S_Bo_b, S_Co_c));
    LR1State nAaBbCd = new LR1State(toSet(S_Ao_a, S_Bo_b, S_Co_d));



    @Test
    public void kernelTest() {
        LR1State one = AaBb;
        LR1State two = AbBa;
        LR1State three = AaCc;
        assertTrue(!one.items.isEmpty());
        assertTrue(!two.items.isEmpty());
        assertTrue(!three.items.isEmpty());
        equalLR0(one,two);
        assertTrue(!(one.equalsLR0(three)));
        assertTrue(!(three.equalsLR0(one)));
        assertTrue(!(three.equalsLR0(two)));
        assertTrue(!(two.equalsLR0(three)));
    }

    @Test
    public void test1() {
        // Test 1 - kein gleicher Kern
        LR1State one = AaBb;
        LR1State two = AaCc;
        assertTrue(!(two.equalsLR0(one)));
        assertTrue(!(one.equalsLR0(two)));
        notWeakCheck(one,two);
    }

    @Test
    public void test2() {
        // Test 2 - Nucleus ist leer, Kerne sind gleich
        LR1State one = AaBb;
        LR1State two = AbBa;
        nucleusEmpty(one);
        nucleusEmpty(two);
        equalLR0(one,two);
        weakCheck(one,two);
    }

    @Test
    public void test3() {
        // Test 3 - Nucleus ist leer, Kerne sind gleich
        LR1State one = AaBb;
        LR1State two = AcBa;
        nucleusEmpty(one);
        nucleusEmpty(two);
        equalLR0(one,two);
        weakCheck(one,two);
    }

    @Test
    public void test4() {
        // Test 4 - Nucleus ist leer, Kerne sind gleich
        LR1State one = AaBb;
        LR1State two = AaBc;
        nucleusEmpty(one);
        nucleusEmpty(two);
        equalLR0(one,two);
        weakCheck(one,two);
    }

    @Test
    public void test5() {
        /* Alle Bedingungen verletzen
         *
         * nAaBb
         * S -> A. | a
         * S -> B. | b
         *
         * nAbBa
         * S -> A. | b
         * S -> B. | a
         */
        LR1State one = nAaBb;
        LR1State two = nAbBa;
        nucleusFull(one);
        nucleusFull(two);
        equalLR0(one,two);
        notWeakCheck(one, two);
    }

    @Test
    public void test6() {
        /*
         * Bedingung a) isoliert verletzen
         *
         * nAa1Bb1
         * S -> A. | a, x1
         * S -> B. | b, x1
         *
         * nAb2Ba2
         * S -> A. | b, x2
         * S -> B. | a, x2
         */
        LR1State one = nAa1Bb1;
        LR1State two = nAb2Ba2;
        nucleusFull(one);
        nucleusFull(two);
        equalLR0(one,two);
        weakCheck(one, two);
    }

    @Test
    public void test7() {
        /* Bedingung c) bzw. b) isoliert verletzen
         *
         * nAa1Bb1
         * S -> A. | a, x1
         * S -> B. | b, x1
         *
         * nAbBa         ( verletzt b) bzw c) )
         * S -> A. | b
         * S -> B. | a
         */
        LR1State one = nAa1Bb1;
        LR1State two = nAbBa;
        nucleusFull(one);
        nucleusFull(two);
        equalLR0(one,two);
        weakCheck(one, two);
    }

    @Test
    public void test8() {

        /*  Bedingung a) und b) bzw. a) und c) verletzen,
         *  während die 2te Bedingung erfüllt wird.
         *
         * nAa1Bb1
         * S -> A. | a, x1
         * S -> B. | b, x1
         *
         * nAbBa         ( verletzt b) bzw c) )
         * S -> A. | b
         * S -> B. | a
        */
        LR1State one = nAa1Bb1;
        LR1State two = nAbBa;
        nucleusFull(one);
        nucleusFull(two);
        equalLR0(one,two);
        weakCheck(one, two);
    }

    @Test
    public void test9() {
        /*  Bedingung b) und c) verletzen,
         *  während Bedingung a) erfüllt wird.
         *
         * nAa1Bb1
         * S -> A. | a
         * S -> B. | b
         * S -> C. | d
         *
         * nAbBa         ( verletzt b) bzw c) )
         * S -> A. | a
         * S -> B. | b
         * S -> C  | c
        */
        LR1State one = nAaBbCd;
        LR1State two = nAaBbCc;
        nucleusFull(one);
        nucleusFull(two);
        equalLR0(one,two);
        weakCheck(one, two);
    }
    
    @Test
    public void test10() {
    	Production X_aAE = new Production(4, N.X, T.a, N.A, N.E);
    	Production Y_aB = new Production(5, N.Y, T.a, N.B);
    	Production Z_aC = new Production(6, N.T, T.a, N.C);
    	
    	LR1Item X_aoAE__b = new LR1Item(X_aAE, 1, lb);
    	LR1Item Y_aoB__d = new LR1Item(Y_aB, 1, ld);
    	LR1Item Z_aoC__a = new LR1Item(Z_aC, 1, la);
    	
    	LR1Item X_aoAE__d = new LR1Item(X_aAE, 1, ld);
    	LR1Item Y_aoB__a = new LR1Item(Y_aB, 1, la);
    	LR1Item Z_aoC__c = new LR1Item(Z_aC, 1, lb);
    	
    	LR1State one = new LR1State(toSet(X_aoAE__b, Y_aoB__d, Z_aoC__a));
    	LR1State two = new LR1State(toSet(X_aoAE__d, Y_aoB__a, Z_aoC__c));
    	
    	nucleusFull(one);
      nucleusFull(two);
      equalLR0(one,two);
      notWeakCheck(one, two);
    }
    
    @Test
    public void test11() {
    	Production A_aDF = new Production(4, N.A, T.a, N.D, N.F);
    	Production C_aDf = new Production(5, N.C, T.a, N.D, T.f);
    	
    	LR1Item A_aDoF__b = new LR1Item(A_aDF, 2, lb);
    	LR1Item C_aDof__a = new LR1Item(C_aDf, 2, la);
    	
    	LR1Item A_aDoF__d = new LR1Item(A_aDF, 2, ld);
    	LR1Item C_aDof__c = new LR1Item(C_aDf, 2, lc);

    	
    	LR1State one = new LR1State(toSet(A_aDoF__b, C_aDof__a));
    	LR1State two = new LR1State(toSet(A_aDoF__d, C_aDof__c));
    	
    	nucleusFull(one);
      nucleusFull(two);
      equalLR0(one,two);
      weakCheck(one, two);
    }

    public HashSet<LR1Item> toSet(LR1Item... items) {
        HashSet<LR1Item> set = new HashSet<LR1Item>();
        for (LR1Item it : items) {
            set.add(it);
        }
        return set;
    }

    public void weakCheck(LR1State one, LR1State two) {
        assertTrue(one.isWeakCompatible(two));
        assertTrue(two.isWeakCompatible(one));
    }

    public void notWeakCheck(LR1State one, LR1State two) {
        assertTrue(!one.isWeakCompatible(two));
        assertTrue(!two.isWeakCompatible(one));
    }

    public void equalLR0(LR1State one, LR1State two) {
        assertTrue(two.equalsLR0(one));
        assertTrue(one.equalsLR0(two));
    }

    public void nucleusFull(LR1State one) {    
    assertTrue(!one.getNucleus().isEmpty());
    }
    
    public void nucleusEmpty(LR1State one) {    
      assertTrue(one.getNucleus().isEmpty());
    }
    
}