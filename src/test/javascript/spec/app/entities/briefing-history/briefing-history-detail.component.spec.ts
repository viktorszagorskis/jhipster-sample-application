import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BriefingHistoryDetailComponent } from 'app/entities/briefing-history/briefing-history-detail.component';
import { BriefingHistory } from 'app/shared/model/briefing-history.model';

describe('Component Tests', () => {
  describe('BriefingHistory Management Detail Component', () => {
    let comp: BriefingHistoryDetailComponent;
    let fixture: ComponentFixture<BriefingHistoryDetailComponent>;
    const route = ({ data: of({ briefingHistory: new BriefingHistory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BriefingHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BriefingHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BriefingHistoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load briefingHistory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.briefingHistory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
