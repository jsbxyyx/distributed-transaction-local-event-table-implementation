var login = angular.module("login", []);
login.controller("loginController", function($scope, $http){
	$scope.submit = function() {
		$http.post("/api/user/addUser", //
			$scope.user, //
			{headers:{'Content-Type':'application/json'}}//
		).then(function(resp){
			var data = resp.data;
			console.log(data);
			if(data.c === 0) {
				alert("添加成功");
			}
		});
	};
});