package ar.edu.unq.eperdemic.persistencia.dao.jdbc


import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.jdbc.JDBCConnector.execute
import java.sql.*


class JDBCPatogenoDAO : PatogenoDAO {

    override fun crear(patogeno: Patogeno): Patogeno {
        return execute { conn: Connection ->
            conn.prepareStatement("INSERT INTO patogeno (tipo, cantidadDeEspecies) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)
                    .use  { ps ->
                        ps.setString(1, patogeno.toString())
                        ps.setInt(2, patogeno.cantidadDeEspecies)

                        ps.execute()

                        val generatedKeys = ps.generatedKeys
                        if (generatedKeys.next()) {
                            val generatedId = generatedKeys.getLong(1)
                            patogeno.id = generatedId
                        }

                        patogeno
                    }
        }
    }


    override fun actualizar(patogeno: Patogeno) {
        execute { conn: Connection ->
            conn.prepareStatement("UPDATE patogeno SET tipo = ?, cantidadDeEspecies = ? WHERE id = ?")
                    .use { ps ->
                        ps.setString(1, patogeno.toString())
                        ps.setInt(2, patogeno.cantidadDeEspecies)
                        //patogeno.id?.let { ps.setLong(3, it) }
                        ps.setLong(3, patogeno.id!!)

                        if(patogeno.id == null) {
                            throw RuntimeException("El id del patogeno no puede ser null")
                        }

                        ps.execute()
                    }
        }
    }

    override fun recuperar(patogenoId: Long): Patogeno {
        return execute { conn: Connection ->
            conn.prepareStatement("SELECT * FROM patogeno WHERE id = ?")
                    .use { ps ->
                        ps.setLong(1, patogenoId)
                        val resultSet = ps.executeQuery()
                        var patogeno: Patogeno? = null

                        while (resultSet.next()) {
                            //si patogeno no es null aca significa que el while dio mas de una vuelta, eso
                            //suele pasar cuando el resultado (resultset) tiene mas de un elemento.
                            if (patogeno != null) {
                                throw RuntimeException("Existe mas de un patogeno con el id $patogenoId")
                            }
                            patogeno = Patogeno(resultSet.getString("tipo"))
                            patogeno.id = resultSet.getLong("id")
                            patogeno.cantidadDeEspecies = resultSet.getInt("cantidadDeEspecies")
                        }

                        patogeno!!
                    }
        }
    }

    override fun recuperarATodos(): List<Patogeno> {
        return execute { conn: Connection ->
            conn.prepareStatement("SELECT * FROM patogeno")
                    .use { ps ->
                        val resultSet = ps.executeQuery()
                        val patogenos = mutableListOf<Patogeno>()

                        while (resultSet.next()) {
                            val patogeno = Patogeno(resultSet.getString("tipo"))
                            patogeno.id = resultSet.getLong("id")
                            patogeno.cantidadDeEspecies = resultSet.getInt("cantidadDeEspecies")
                            patogenos.add(patogeno)
                        }
                        patogenos
                    }
        }
    }

    init {
        val initializeScript = javaClass.classLoader.getResource("createAll.sql").readText()
        execute {
            val ps = it.prepareStatement(initializeScript)
            ps.execute()
            ps.close()
            null
        }
    }

}