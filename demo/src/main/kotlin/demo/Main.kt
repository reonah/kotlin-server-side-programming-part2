package demo

import demo.database.*
import demo.database.UserDynamicSqlSupport.User.id
import demo.database.UserDynamicSqlSupport.User.name
import demo.database.UserDynamicSqlSupport.User.profile
import org.apache.ibatis.io.Resources
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.mybatis.dynamic.sql.SqlBuilder.isEqualTo
import org.mybatis.dynamic.sql.SqlBuilder.isLessThan

fun main() {
    list543()
    list5410()
    list5412()
    list5414()
    list5416()
    list5418()
    list5420()
    list5422()
}

fun createSessionFactory(): SqlSessionFactory {
    val resource = "mybatis-config.xml"
    val inputStream = Resources.getResourceAsStream(resource)
    return SqlSessionFactoryBuilder().build(inputStream)
}

fun list543() {
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val userList = mapper.select {
            where(id, isLessThan(1000))
        }
        println(userList)
    }
}

fun list5410() {
    val user = UserRecord(103, "Shiro", 18, "Hello")
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.insert(user)
        session.commit()
        println("${count}行のレコードを挿入しました")
    }
}

fun list5412() {
    val userList = listOf(UserRecord(104, "Goro", 15, "Hello"), UserRecord(105, "Rokuro", 13, "Hello"))
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.insertMultiple(userList)
        session.commit()
        println("${count}行のレコードを挿入しました")
    }
}

fun list5414() {
    val user = UserRecord(id = 105, profile = "Bye")
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.updateByPrimaryKeySelective(user)
        session.commit()
        println("${count}行のレコードを更新しました")
    }
}

fun list5416() {
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.update {
            set(profile).equalTo("Hey")
            where(id, isEqualTo(104))
        }
        session.commit()
        println("${count}行のレコードを更新しました")
    }
}

fun list5418() {
    val user = UserRecord(profile = "Good Morning")
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.update {
            updateSelectiveColumns(user)
            where(name, isEqualTo("Shiro"))
        }
        session.commit()
        println("${count}行のレコードを更新しました")
    }
}

fun list5420() {
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.deleteByPrimaryKey(102)
        session.commit()
        println("${count}行のレコードを削除しました")
    }
}

fun list5422() {
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        val count = mapper.delete {
            where(name, isEqualTo("Jiro"))
        }
        session.commit()
        println("${count}行のレコードを削除しました")
    }
}
