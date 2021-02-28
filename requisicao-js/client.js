function consultarRestaurantes() {
  $.ajax({
    url: "http://localhost:8068/restaurantes",
    type: "get",
    headers: {
      "X-teste": "dcknfjskdnfksfbnjks"
    }, 
    

    success: function(response) {
      $("#conteudo").text(JSON.stringify(response));
    }
  });
}

function fecharRestaurante() {
  $.ajax({
    url: "http://localhost:8068/restaurantes/1/fechamento",
    type: "put",

    success: function(response) {
      alert("Restaurante foi fechado!");
    }
  });
}

$("#botao").click(consultarRestaurantes);