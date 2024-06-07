import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-section',
  templateUrl: './section.component.html',
  styleUrl: './section.component.css'
})
export class SectionComponent implements OnInit {
  constructor(private route: Router){}
  ngOnInit(): void {
    let token = localStorage.getItem("token");
    if(token == null){
      this.route.navigateByUrl('/login');
    }
  }

}
