package ar.edu.unq.eperdemic.testServicios

/*
@TestInstance(PER_CLASS)
class VectorServiceTest {

    lateinit var service: VectorService
    lateinit var serviceUbicacion: UbicacionService
    lateinit var serviceEspecie: EspecieService
    lateinit var servicePatogeno: PatogenoService
    lateinit var dataService: DataService

    lateinit var especie   : Especie
    lateinit var patogeno  : Patogeno

    lateinit var humano: Vector
    lateinit var golondrina: Vector
    lateinit var ubicacion: Ubicacion

    lateinit var random : RandomGenerator

    @BeforeEach
    fun prepare() {

        this.service = VectorServiceImp(HibernateVectorDAO(), HibernateEspecieDAO())
        this.serviceUbicacion = UbicacionServiceImp(HibernateUbicacionDAO(), HibernateVectorDAO())
        this.serviceEspecie = EspecieServiceImpl(HibernateEspecieDAO(), HibernateVectorDAO())
        this.servicePatogeno  = PatogenoServiceImpl(HibernatePatogenoDAO(), HibernateEspecieDAO(), HibernateUbicacionDAO(), HibernateVectorDAO())
        this.dataService = DataServiceImpl(HibernateDataDAO())

        ubicacion = Ubicacion("Argentina")
        humano     = Vector("Pedro", ubicacion, TipoVector.HUMANO)
        golondrina = Vector("Pepita", ubicacion, TipoVector.ANIMAL)

        patogeno  = Patogeno("Wachiturro", 90, 9, 9, 9, 67)

        serviceUbicacion.crear(ubicacion)
        service.crear(humano)

        servicePatogeno.crear(patogeno)

        especie = servicePatogeno.agregarEspecie(patogeno.getId()!!, "Bacteria", ubicacion.getId()!!)

        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setNumeroGlobal(0)

    }


    @Test
    fun testDeCreacionDeUnVector(){
        val pepita = service.crear(golondrina)

        Assertions.assertEquals(2, pepita.getId())
    }

    @Test
    fun testDeActualizacionDeUnVector(){
        val pepita = service.crear(golondrina)

        Assertions.assertEquals("Pepita", pepita.nombre)

        golondrina.nombre = "Marta"
        service.updatear(pepita)

        Assertions.assertEquals("Marta", pepita.nombre)
    }

    @Test
    fun testDeRecuperarUnVector(){
        val otroHumano = service.recuperar(humano.getId()!!)

        Assertions.assertEquals(otroHumano.getId(), humano.getId())
        Assertions.assertEquals(otroHumano.nombre, humano.nombre)
    }

    @Test
    fun testDeRecuperarTodosLosVectores(){
        val pepita = service.crear(golondrina)

        val listaDeVectores = service.recuperarTodos()

        Assertions.assertEquals(humano.getId(), listaDeVectores.get(0).getId())
        Assertions.assertEquals(humano.nombre, listaDeVectores.get(0).nombre)
        Assertions.assertEquals(pepita.getId(), listaDeVectores.get(1).getId())
        Assertions.assertEquals(pepita.nombre, listaDeVectores.get(1).nombre)
    }

    @Test
    fun testDeInfectarAUnVector(){

        val pepita = service.crear(golondrina)

        Assertions.assertFalse(pepita.estaInfectado())

        service.infectar(pepita.getId()!!, especie.getId()!!)

        val otraGolondrina = service.recuperar(pepita.getId()!!)

        Assertions.assertTrue(otraGolondrina.estaInfectado())
        Assertions.assertEquals(otraGolondrina.enfermedadesDelVector().first().getId(), especie.getId())

    }

    @Test
    fun testDeEnfermedadesDeUnVector(){

        val pepita = service.crear(golondrina)

        service.infectar(pepita.getId()!!, especie.getId()!!)

        Assertions.assertFalse(service.enfermedades(pepita.getId()!!).isEmpty())
        Assertions.assertEquals(service.recuperar(pepita.getId()!!).enfermedadesDelVector().first().getId(), especie.getId())

        service.infectar(pepita.getId()!!, especie.getId()!!)

        Assertions.assertTrue(service.recuperar(pepita.getId()!!).estaInfectado())
    }

    @Test
    fun testSeTrataDeRecuperarUnVectorQueNoExiste() {

        Assertions.assertThrows(NoExisteElVector::class.java) {
            service.recuperar(15)
        }

    }

    @AfterEach
    fun cleanup() {
        dataService.cleanAll()
    }

}

 */