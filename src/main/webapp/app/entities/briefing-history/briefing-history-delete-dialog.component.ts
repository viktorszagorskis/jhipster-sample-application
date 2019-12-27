import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBriefingHistory } from 'app/shared/model/briefing-history.model';
import { BriefingHistoryService } from './briefing-history.service';

@Component({
  templateUrl: './briefing-history-delete-dialog.component.html'
})
export class BriefingHistoryDeleteDialogComponent {
  briefingHistory?: IBriefingHistory;

  constructor(
    protected briefingHistoryService: BriefingHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.briefingHistoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('briefingHistoryListModification');
      this.activeModal.close();
    });
  }
}
