package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.vector.VectorElastic
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface VectorElasticDAO : ElasticsearchRepository<VectorElastic, Long> {

}