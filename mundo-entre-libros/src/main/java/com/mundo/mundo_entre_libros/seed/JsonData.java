package com.mundo.mundo_entre_libros.seed;

import java.util.List;

public class JsonData {

    private List<CategoryJson> categorias;

    private List<BookJson> libros;

    private List<SagaJson> sagas;

    public List<CategoryJson> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoryJson> categorias) {
        this.categorias = categorias;
    }

    public List<BookJson> getLibros() {
        return libros;
    }

    public void setLibros(List<BookJson> libros) {
        this.libros = libros;
    }

    public List<SagaJson> getSagas() {
        return sagas;
    }

    public void setSagas(List<SagaJson> sagas) {
        this.sagas = sagas;
    }

}
