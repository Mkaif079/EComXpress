package com.example.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class UnderstandDifferentAssertions {

    @Test
    public void onePlusOne(){
        //Arrange
            int a = 1;
            int b =1 ;
        //Act
            int x = a+ b;
        //Assert

            //assert x == 2;
            Assertions.assertEquals(2, x);
            Assertions.assertThrows(ArithmeticException.class , ()->divideByZero());
            Assertions.assertNull(removeElement());
            Assertions.assertNotEquals(3,x);

            int[] input = {4,2,3,1,5};
            int[] expected = {1,2,3,4,5};

            Arrays.sort(input);
            for(int i = 0 ; i<input.length ; i++){
                assert input[i] == expected[i];
            }
            Assertions.assertArrayEquals(input , expected);

            List<String> student1 = Arrays.asList("Kaif","Bilal","Saurabh");
            List<String> student2 = Arrays.asList("Kaif","Bilal","Saurabh");

            Assertions.assertLinesMatch(student1 , student2);

            Assertions.assertTimeout(Duration.ofSeconds(2) , ()->maxSum(input));
    }

    public  int maxSum(int[] numbers) throws InterruptedException {
        //Thread.sleep(3000);
        return 10;
    }
    private String removeElement() {
        return null;
    }

    public int divideByZero(){
        return 1/0;
    }
}
