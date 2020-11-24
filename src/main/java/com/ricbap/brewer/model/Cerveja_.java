package com.ricbap.brewer.model;

import java.math.BigDecimal;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Cerveja.class)
public class Cerveja_ {

	public static volatile SingularAttribute<Cerveja, Long> codigo;
	public static volatile SingularAttribute<Cerveja, String> sku;
	public static volatile SingularAttribute<Cerveja, String> nome;
	public static volatile SingularAttribute<Cerveja, String> descricao;
	public static volatile SingularAttribute<Cerveja, BigDecimal> valor;
	public static volatile SingularAttribute<Cerveja, BigDecimal> teorAlcoolico;
	public static volatile SingularAttribute<Cerveja, BigDecimal> comissao;
	public static volatile SingularAttribute<Cerveja, Integer> quantidadeEstoque;
	public static volatile SingularAttribute<Cerveja, Origem> origem;
	public static volatile SingularAttribute<Cerveja, Sabor> sabor;
	public static volatile SingularAttribute<Cerveja, Estilo> estilo;
	public static volatile SingularAttribute<Cerveja, String> foto;
	public static volatile SingularAttribute<Cerveja, String> contentType;

}


