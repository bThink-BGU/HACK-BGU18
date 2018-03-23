import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChartsModule as Ng2Charts } from 'ng2-charts';
import { AngularFirestore } from 'angularfire2/firestore';

//import { ConfigComponent } from '../../config/config.component';
import { ChartsRoutingModule } from './charts-routing.module';
import { ChartsComponent } from './charts.component';
import { PageHeaderModule } from '../../shared';
import {ConfigService} from '../../config/config.service';

@NgModule({
    imports: [CommonModule, Ng2Charts, ChartsRoutingModule, PageHeaderModule],
    declarations: [ChartsComponent],
    providers: [
        ConfigService,
        AngularFirestore
    ]
})
export class ChartsModule {}
