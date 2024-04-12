package ar.edu.unq.unidad3.dao.helper.service

import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx
import ar.edu.unq.unidad3.dao.helper.dao.DataDAO

class DataServiceImpl (
    private val dataDAO: DataDAO
) : DataService {

    override fun cleanAll() {
        runTrx {
            dataDAO.clear()
        }
    }

}
