import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { UserService } from '../service/user.service';
import { RequestForDeleting } from './RequestForDeleting';

@Component({
  selector: 'app-request-for-deleting-account',
  templateUrl: './request-for-deleting-account.component.html',
  styleUrls: ['./request-for-deleting-account.component.css']
})
export class RequestForDeletingAccountComponent implements OnInit {

  requestForDeleting = new RequestForDeleting("", "");


  constructor(private userService: UserService, private toastr:ToastrService) { }

  ngOnInit(): void {
  }

  sendRequest(){
    this.userService.sendRequestForDeletingAccount(this.requestForDeleting).subscribe((data)=>{
      if(data == null){
        this.toastr.error("Neuspešno slanje zahteva. Već imate zahteve koji čekaju odobravanje administratora.")
      }
      else{
        this.toastr.success("Uspešno ste poslali zahtev")
        this.requestForDeleting = new RequestForDeleting("", "");
      }
    },
    (error)=>{
      this.toastr.success(error)
    });
  }

}
