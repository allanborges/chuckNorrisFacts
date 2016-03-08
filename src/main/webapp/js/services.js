
angular.module('chuckFactsApp').factory('chuckFactsService', ['$resource', function($resource){

   var URL = "http://localhost:8080/ChuckNorrisFacts/rest/facts";

   
   /*
		se deixar sem o caracter @ é um parametro fixo, se não é um parametro que pode ou não
  		ser passado
   */
   var service = $resource(URL + "/:counts",

   				 {counts:"@counts"},

   				 {
	   				'save': {                     
                        isArray: false,
                        method: 'POST'              		           		
	            	}
            	 }
            	);
	
   return service;
	
}])