import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AngularFirestore } from 'angularfire2/firestore';

import { ProfileRoutingModule } from './profile-routing.module';
import { ProfileComponent } from './profile.component';
import { PageHeaderModule } from './../../shared';

@NgModule({
    imports: [CommonModule, ProfileRoutingModule, PageHeaderModule],
    declarations: [ProfileComponent],
    providers: [AngularFirestore]
})
export class ProfileModule {
	
	//public firebase;
	
	constructor() {
    }
    
    gOnInit() {}
}