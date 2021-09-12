
    function deleteUser(id){
     swal({
  title: "Are you sure?",
  text: "you will be delete!",
  icon: "warning",
  buttons: true,
  dangerMode: true,
})
.then((willDelete) => {
  if (willDelete) {
     window.location="/user/delete/"+id;
    swal("Poof! Your imaginary contact has been deleted!", {
      icon: "success",
    });
  } else {
    swal("Your imaginary data is safe!");
  }
});
    };
function deleteCategory(id){
     swal({
  title: "Are you sure?",
  text: "you will be delete!",
  icon: "warning",
  buttons: true,
  dangerMode: true,
})
.then((willDelete) => {
  if (willDelete) {
     window.location="/category/delete/"+id;
    swal("Poof! Your imaginary contact has been deleted!", {
      icon: "success",
    });
  } else {
    swal("Your imaginary data is safe!");
  }
});
    };
function deleteArchive(id){
     swal({
  title: "Are you sure?",
  text: "you will be delete!",
  icon: "warning",
  buttons: true,
  dangerMode: true,
})
.then((willDelete) => {
  if (willDelete) {
     window.location="/archive/delete/"+id;
    swal("Poof! Your imaginary contact has been deleted!", {
      icon: "success",
    });
  } else {
    swal("Your imaginary data is safe!");
  }
});
    };    
    
