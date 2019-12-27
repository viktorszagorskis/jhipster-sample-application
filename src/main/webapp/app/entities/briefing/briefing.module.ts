import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { BriefingComponent } from './briefing.component';
import { BriefingDetailComponent } from './briefing-detail.component';
import { BriefingUpdateComponent } from './briefing-update.component';
import { BriefingDeleteDialogComponent } from './briefing-delete-dialog.component';
import { briefingRoute } from './briefing.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(briefingRoute)],
  declarations: [BriefingComponent, BriefingDetailComponent, BriefingUpdateComponent, BriefingDeleteDialogComponent],
  entryComponents: [BriefingDeleteDialogComponent]
})
export class JhipsterSampleApplicationBriefingModule {}
