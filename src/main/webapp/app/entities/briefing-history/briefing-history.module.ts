import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { BriefingHistoryComponent } from './briefing-history.component';
import { BriefingHistoryDetailComponent } from './briefing-history-detail.component';
import { BriefingHistoryUpdateComponent } from './briefing-history-update.component';
import { BriefingHistoryDeleteDialogComponent } from './briefing-history-delete-dialog.component';
import { briefingHistoryRoute } from './briefing-history.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(briefingHistoryRoute)],
  declarations: [
    BriefingHistoryComponent,
    BriefingHistoryDetailComponent,
    BriefingHistoryUpdateComponent,
    BriefingHistoryDeleteDialogComponent
  ],
  entryComponents: [BriefingHistoryDeleteDialogComponent]
})
export class JhipsterSampleApplicationBriefingHistoryModule {}
