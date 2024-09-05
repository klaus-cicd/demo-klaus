package com.klaus.demo.helloworld.test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.klaus.demo.comm.entity.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * @author Silas
 */
public class KryoTest {

    public static void main(String[] args) throws FileNotFoundException {
        Kryo kryo = new Kryo();
        kryo.register(User.class);
        User user = new User();
        user.setId(13123L);
        user.setUsername("klaus");

        // 将User对象转为字节数组流，写入ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Output output = new Output(byteArrayOutputStream);
        kryo.writeObject(output, user);
        output.close();
        System.out.println(Arrays.toString(byteArrayOutputStream.toByteArray()));

        // 将字节数组反序列化为对象
        Input input = new Input(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        User user2 = kryo.readObject(input, User.class);
        input.close();
        System.out.println(user2);
    }
}
