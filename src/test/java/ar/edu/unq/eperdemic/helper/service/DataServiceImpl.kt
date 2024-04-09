package ar.edu.unq.eperdemic.helper.service

import ar.edu.unq.eperdemic.helper.dao.DataDAO
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx

class DataServiceImpl (
    private val dataDAO: DataDAO
) : DataService {

    override fun cleanAll() {
        runTrx {
            dataDAO.clear()
        }
    }

}