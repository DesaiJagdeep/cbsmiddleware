import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKarkhanaVasuli, NewKarkhanaVasuli } from '../karkhana-vasuli.model';

export type PartialUpdateKarkhanaVasuli = Partial<IKarkhanaVasuli> & Pick<IKarkhanaVasuli, 'id'>;

export type EntityResponseType = HttpResponse<IKarkhanaVasuli>;
export type EntityArrayResponseType = HttpResponse<IKarkhanaVasuli[]>;

@Injectable({ providedIn: 'root' })
export class KarkhanaVasuliService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/karkhana-vasulis');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(karkhanaVasuli: NewKarkhanaVasuli): Observable<EntityResponseType> {
    return this.http.post<IKarkhanaVasuli>(this.resourceUrl, karkhanaVasuli, { observe: 'response' });
  }

  update(karkhanaVasuli: IKarkhanaVasuli): Observable<EntityResponseType> {
    return this.http.put<IKarkhanaVasuli>(`${this.resourceUrl}/${this.getKarkhanaVasuliIdentifier(karkhanaVasuli)}`, karkhanaVasuli, {
      observe: 'response',
    });
  }

  partialUpdate(karkhanaVasuli: PartialUpdateKarkhanaVasuli): Observable<EntityResponseType> {
    return this.http.patch<IKarkhanaVasuli>(`${this.resourceUrl}/${this.getKarkhanaVasuliIdentifier(karkhanaVasuli)}`, karkhanaVasuli, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IKarkhanaVasuli>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKarkhanaVasuli[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getKarkhanaVasuliIdentifier(karkhanaVasuli: Pick<IKarkhanaVasuli, 'id'>): number {
    return karkhanaVasuli.id;
  }

  compareKarkhanaVasuli(o1: Pick<IKarkhanaVasuli, 'id'> | null, o2: Pick<IKarkhanaVasuli, 'id'> | null): boolean {
    return o1 && o2 ? this.getKarkhanaVasuliIdentifier(o1) === this.getKarkhanaVasuliIdentifier(o2) : o1 === o2;
  }

  addKarkhanaVasuliToCollectionIfMissing<Type extends Pick<IKarkhanaVasuli, 'id'>>(
    karkhanaVasuliCollection: Type[],
    ...karkhanaVasulisToCheck: (Type | null | undefined)[]
  ): Type[] {
    const karkhanaVasulis: Type[] = karkhanaVasulisToCheck.filter(isPresent);
    if (karkhanaVasulis.length > 0) {
      const karkhanaVasuliCollectionIdentifiers = karkhanaVasuliCollection.map(
        karkhanaVasuliItem => this.getKarkhanaVasuliIdentifier(karkhanaVasuliItem)!
      );
      const karkhanaVasulisToAdd = karkhanaVasulis.filter(karkhanaVasuliItem => {
        const karkhanaVasuliIdentifier = this.getKarkhanaVasuliIdentifier(karkhanaVasuliItem);
        if (karkhanaVasuliCollectionIdentifiers.includes(karkhanaVasuliIdentifier)) {
          return false;
        }
        karkhanaVasuliCollectionIdentifiers.push(karkhanaVasuliIdentifier);
        return true;
      });
      return [...karkhanaVasulisToAdd, ...karkhanaVasuliCollection];
    }
    return karkhanaVasuliCollection;
  }
}
