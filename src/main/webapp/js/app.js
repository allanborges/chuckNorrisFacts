
angular.module('chuckFactsApp', ['ngResource'])
.controller('factsCtroller', ['chuckFactsService','$q', function(chuckFactsService,$q){
	
	var self = this;

	self.facts = [];

	self.newFact = {id: 2, factText: 'teste'};

	var maxPage = 30,
		qtPage = 0;

	self.filter = '';	
	self.showLoader = false;


	function mountPagenate(data){
		if (data && data.totalFacts === 0)
			return $q.reject();
			
		qtPage = Math.ceil(data.totalFacts/maxPage);
		
		return $q.resolve();
	}	

	self.onLoadFacts = function(page){		
		self.showLoader = true;
		chuckFactsService.get({counts: "counts", search: self.filter}).$promise.then(function(data){			
			mountPagenate(data);
		}).then(function(){
			self.getFactsByPage(page);
		},function(){
			self.showLoader = false;
			console.log("colocar exibição do erro");	
		});		
	}

	self.getFactsByPage = function(numberPage){
		chuckFactsService.query({qtFacts: maxPage, page: numberPage, search: self.filter}).$promise.then(function(data){
			self.showLoader = false;
			self.facts = data;
		});
	}

	self.getPages = function(){
		var pages = [];

		for (var i = 1 ; i < qtPage ; i++){
			pages.push(i);
		}

		return pages;
	}

	self.sendFact = function(){
		console.log(self.fact);
		chuckFactsService.save({fact: self.fact});
	}

	
}])
