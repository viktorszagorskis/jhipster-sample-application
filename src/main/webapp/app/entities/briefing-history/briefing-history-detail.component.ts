import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBriefingHistory } from 'app/shared/model/briefing-history.model';

@Component({
  selector: 'jhi-briefing-history-detail',
  templateUrl: './briefing-history-detail.component.html'
})
export class BriefingHistoryDetailComponent implements OnInit {
  briefingHistory: IBriefingHistory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ briefingHistory }) => {
      this.briefingHistory = briefingHistory;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
