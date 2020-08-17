import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RouteServiceService } from '../service/route-service.service';
import { Route } from '../model/route';
import {Nodes} from "../model/nodes";

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent {

  routeObj: Route;
  nodes: Nodes;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private routeService: RouteServiceService) {
    this.routeObj = new Route();
  }

  onSubmit() {
    this.routeService.findShortestPath(this.routeObj).subscribe(response => {
      this.nodes = new Nodes();
      this.nodes.nameList = response;
    });
  }

}
