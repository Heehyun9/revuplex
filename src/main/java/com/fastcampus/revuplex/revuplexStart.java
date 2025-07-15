package com.fastcampus.revuplex;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class revuplexStart implements CommandLineRunner {
    @Autowired
    EntityManagerFactory emf;

    public static void main(String[] args) {

//        SpringApplication app = new SpringApplication(revuplexStart.class);
//        app.setWebApplicationType(WebApplicationType.NONE);  //not webapp -- tomcat 안뜸
//        app.run(args);
        SpringApplication.run(revuplexStart.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        EntityManager em = emf.createEntityManager();
//        System.out.println("em = " + em);
//        EntityTransaction tx = em.getTransaction();
//
//
//        User user = new User();
//        user.setId("aaa");
//        user.setPassword("1234");
//        user.setName("Lee");
//        user.setEmail("aaa@aaa.com");
//        user.setInDate(new Date());
//        user.setUpDate(new Date());
//
//        tx.begin();  //트랜잭션 시작
//        //1. 저장
//        em.persist(user); //user 엔티티를 em에 영속화(저장)
//        em.persist(user); //같은 user엔티티를 여러번 저장해도 한번만 insert
//
//        //2. 변경
//        user.setPassword("43421");  //PersistenceContext가 변경 감지. update
//        user.setEmail("bbb@bbb.com");  //PersistenceContext가 변경 감지. update
//        tx.commit();  //트랜잭션 종료(DB에 반영)
//
//        //3. 조회
//        User user2 = em.find(User.class, "aaa");
//        System.out.println("user==user2=" + (user==user2));
//        User user3 = em.find(User.class, "bbb");
//        System.out.println("user3 = "+ user3);
//
//        //4. 삭제
//        tx.begin();
//        em.remove(user);  //em에서 user 엔티티를 삭제 ---> commit 해야 반영
//        tx.commit();
    }
}
