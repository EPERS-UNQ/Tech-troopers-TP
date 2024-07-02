package ar.edu.unq.eperdemic.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories

@Configuration
class AppConfiguration {
}


@Configuration
@EnableElasticsearchRepositories(basePackages = [".eperdemic.persistencia.dao.VectorElasticDAO"])
class ElasticsearchConfig {

    @Bean
    fun indexNameProvider(): IndexNameProvider {
        return IndexNameProvider("vectorelastic")
    }
}

class IndexNameProvider(val indexName: String)
