import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBriefing } from 'app/shared/model/briefing.model';
import { BriefingService } from './briefing.service';

@Component({
  templateUrl: './briefing-delete-dialog.component.html'
})
export class BriefingDeleteDialogComponent {
  briefing?: IBriefing;

  constructor(protected briefingService: BriefingService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.briefingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('briefingListModification');
      this.activeModal.close();
    });
  }
}
