import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../../router.animations';
import { AngularFirestore, AngularFirestoreDocument } from 'angularfire2/firestore';
import { Observable } from 'rxjs/Observable';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss'],
    animations: [routerTransition()]
})
export class ProfileComponent<Item> {
	private itemDoc: AngularFirestoreDocument<Item>;
	item: Observable<Item>;
	constructor(private afs: AngularFirestore) {
		this.itemDoc = afs.doc<Item>('user/owGR0jSCXX3Jv05RciE6');
	    this.item = this.itemDoc.valueChanges();
	}
	update(item: Item) {
		this.itemDoc.update(item);
	}
    
	ngOnInit() { }
}
