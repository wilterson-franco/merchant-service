package com.wilterson.cms.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StringGeneratorTest {

    @Test
    @DisplayName("given a StringGenerator instance " +
            "when a random string is generated " +
            "then it should work properly")
    void whenInstantiate_thenShouldSucceed() {

        // given
        var stringGenerator = new StringGenerator();

        // when
        String random = stringGenerator.generate(1);

        // then
        assertThat(random).hasSize(1);
    }

    @Test
    @DisplayName("given a length of 0 " +
            "when a random string is generated " +
            "then an exception should be thrown")
    void givenLength0_whenStringGenerated_thenException() {

        // given
        int length = 0;

        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> StringGenerator.generate(length));
    }

    @Test
    @DisplayName("given a length of 1 " +
            "when a random string is generated " +
            "then the length should be 1")
    void givenLength1_whenStringGenerated_thenLength1() {

        // given
        int length = 1;

        // when
        String random = StringGenerator.generate(length);

        // then
        assertThat(random).hasSize(1);
    }

    @Test
    @DisplayName("given a length of 10 " +
            "when a random string is generated " +
            "then the length should be 10")
    void givenLength10_whenStringGenerated_thenLength10() {

        // given
        int length = 10;

        // when
        String random = StringGenerator.generate(length);

        // then
        assertThat(random).hasSize(10);
    }

    @Test
    @DisplayName("given a length of 50 " +
            "when a random string is generated " +
            "then the length should be 50")
    void givenLength50_whenStringGenerated_thenLength50() {

        // given
        int length = 50;

        // when
        String random = StringGenerator.generate(length);

        // then
        assertThat(random).hasSize(50);
    }

    @Test
    @DisplayName("given a length of 51 " +
            "when a random string is generated " +
            "then the length should be 51")
    void givenLength51_whenStringGenerated_thenLength51() {

        // given
        int length = 51;

        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> StringGenerator.generate(length));
    }
}