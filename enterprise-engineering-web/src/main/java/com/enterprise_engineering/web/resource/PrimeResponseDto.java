package com.enterprise_engineering.web.resource;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "prime-response")
public class PrimeResponseDto {

    private final int initial;

    @JacksonXmlElementWrapper(localName = "primes")
    @JacksonXmlProperty(localName = "prime")
    private final List<Integer> primes;

    public PrimeResponseDto(PrimeResponseDtoBuilder builder) {
        this.initial = builder.initial;
        this.primes = builder.primes;
    }

    public int getInitial() { return initial; }

    public List<Integer> getPrimes() {
        return primes;
    }

    public static final class PrimeResponseDtoBuilder {

        private int initial;
        private List<Integer> primes;

        private PrimeResponseDtoBuilder() {}

        public static PrimeResponseDtoBuilder newPrimeResponseDtoBuilder() {
            return new PrimeResponseDtoBuilder();
        }

        public PrimeResponseDtoBuilder withInitial(int initial) {
            this.initial = initial;
            return this;
        }

        public PrimeResponseDtoBuilder withPrimes(List<Integer> primes) {
            this.primes = primes;
            return this;
        }

        public PrimeResponseDto build() {
            return new PrimeResponseDto(this);
        }
    }

}