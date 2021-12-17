package com.seceh.basic.utils.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.seceh.basic.utils.file.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

class JsonUtilsTest {

    static class Vo implements Serializable {
        private int id;
        private String name;

        public Vo() {
        }

        public Vo(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private final Vo vo = new Vo(1, "first");
    private final String line = "{\"id\":1,\"name\":\"first\"}";
    private final String lines = FileUtils.getStringFromResource("testObject2Lines.json");

    @Test
    void testObject2Line() {
        Assertions.assertEquals(line, JsonUtils.object2Line(vo));
    }

    @Test
    void testObject2Lines() {
        Assertions.assertEquals(lines,
                JsonUtils.object2Lines(vo).replace("\r", ""));
    }

    @Test
    void testString2Object() {
        Vo vo = JsonUtils.string2Object(line, Vo.class);
        Assertions.assertNotNull(vo);
        Assertions.assertEquals("first", vo.getName());
    }

    @Test
    void testString2ObjectError() {
        Vo vo = JsonUtils.string2Object("", Vo.class);
        Assertions.assertNull(vo);
    }

    @Test
    void testString2Type() {
        Vo vo = JsonUtils.string2Type(line, new TypeReference<Vo>() {
        });
        Assertions.assertNotNull(vo);
        Assertions.assertEquals("first", vo.getName());
    }

    @Test
    void testString2TypeError() {
        Vo vo = JsonUtils.string2Type("", new TypeReference<Vo>() {
        });
        Assertions.assertNull(vo);
    }

    @Test
    void testResource2Type() {
        Vo vo = JsonUtils.resource2Type("testObject2Lines", new TypeReference<Vo>() {
        });
        Assertions.assertNotNull(vo);
        Assertions.assertEquals("first", vo.getName());
    }

    @Test
    void testResource2TypeError() {
        Vo vo = JsonUtils.resource2Type("", new TypeReference<Vo>() {
        });
        Assertions.assertNull(vo);
    }
}
