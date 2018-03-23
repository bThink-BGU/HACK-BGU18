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

    public barChartOptions: any = {
        scaleShowVerticalLines: false,
        responsive: true
    };
    public barChartLabels: string[] = [
        '2006',
        '2007',
        '2008',
        '2009',
        '2010',
        '2011',
        '2012'
    ];
    public barChartType: string = 'bar';
    public barChartLegend: boolean = true;

    public barChartData: any[] = [
        { data: [65, 59, 80, 81, 56, 55, 40], label: 'סמסטר א' },
        { data: [28, 48, 40, 19, 86, 27, 90], label: 'סמסטר ב' }
    ];

    // Doughnut
    public doughnutChartLabels: string[] = [
        'נק"ז שנצבר',
        'נק"ז שנשאר'
    ];
    public doughnutChartData: number[] = [350, 450];
    public doughnutChartType: string = 'doughnut';


    ngOnInit() {

    }
}
