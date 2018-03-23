import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../../router.animations';
import { Config, ConfigService } from '../../config/config.service';
import {Observable} from 'rxjs/Observable';
import { AsyncPipe } from '@angular/common';
import { AngularFirestore, AngularFirestoreDocument, AngularFirestoreCollection } from 'angularfire2/firestore';

@Component({
    selector: 'app-charts',
    templateUrl: './charts.component.html',
    styleUrls: ['./charts.component.scss'],
    animations: [routerTransition()]
})
export class ChartsComponent<Course, User> implements OnInit {
	private courseCollection: AngularFirestoreCollection<Course>;
	private userCollection: AngularFirestoreCollection<User>;
	courses: Observable<Course[]>;   
	users: Observable<User[]>;   
	hoveredCourse;
    
    constructor(/*private configService: ConfigService, */private afs: AngularFirestore) {
		this.courseCollection = afs.collection<Course>('course');
    	this.courses = this.courseCollection.valueChanges();
	}
  	
  	onMouseOver($course) {
  		this.hoveredCourse = $course;
		this.userCollection = this.afs.collection<User>('user');
    	this.users = this.userCollection.valueChanges();
  	}
  	
  	onMouseLeave() {
  		this.hoveredCourse = null;
  		this.userCollection = null;
    	this.users = null;
  	}


    /*showConfig() {
        this.configService.getConfig_1()
            .subscribe(data => this.config = data);
             this.obj = JSON.parse(this.config);
    }*/

    ngOnInit() {
        //this.showConfig();
    }
}
