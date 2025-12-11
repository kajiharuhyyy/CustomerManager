package com.kajiharuhyyy.customermanager.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kajiharuhyyy.customermanager.domain.Customer;
import com.kajiharuhyyy.customermanager.domain.CustomerRank;
import com.kajiharuhyyy.customermanager.domain.CustomerStatus;

@SpringBootTest
class CustomerRepositoryTest {

        @Autowired
        private CustomerRepository customerRepository;

        @Test
        @DisplayName("名前の部分一致（大文字・小文字無視）で検索できること")
        void testFindByNameContainingIgnoreCase_正常系() {

                Customer yamada = Customer.builder()
                        .name("山田 太郎")
                        .email("yamada@example.com")
                        .phone("0900000000")
                        .rank(CustomerRank.A)
                        .status(CustomerStatus.IN_PROGRESS)
                        .memo("テストユーザー")
                        .active(true)
                        .build();

                Customer kajino = Customer.builder()
                        .name("梶野 悠久")
                        .email("kajino@example.com")
                        .phone("0901111111")
                        .rank(CustomerRank.B)
                        .status(CustomerStatus.NEW)
                        .memo("別のユーザー")
                        .active(true)
                        .build();

                customerRepository.save(yamada);
                customerRepository.save(kajino);

                List<Customer> result = customerRepository.findByNameContainingIgnoreCase("山田");

                assertThat(result)
                        .hasSize(1)
                        .first()
                        .extracting(Customer::getName)
                        .isEqualTo("山田 太郎");
        }

        @Test
        @DisplayName("active=trueの顧客だけ取得できること")
        void findByActive_true_onry() {

                Customer activeUser = Customer.builder()
                        .name("アクティブ太郎")
                        .email("active@example.com")
                        .phone("09012345678")
                        .rank(CustomerRank.A)
                        .status(CustomerStatus.NEW)
                        .memo("active user")
                        .active(true)
                        .build();

                Customer inactiveUser = Customer.builder()
                        .name("非アクティブ次郎")
                        .email("inactive@example.com")
                        .phone("09099998888")
                        .rank(CustomerRank.B)
                        .status(CustomerStatus.LOST)
                        .memo("inactive user")
                        .active(false)
                        .build();

                customerRepository.save(activeUser);
                customerRepository.save(inactiveUser);

                List<Customer> result = customerRepository.findByActive(true);

                assertThat(result)
                        .hasSize(1)
                        .first()
                        .extracting(Customer::isActive)
                        .isEqualTo(true);

        }


}